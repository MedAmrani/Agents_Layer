import org.apache.commons.io.IOUtils
import java.nio.charset.StandardCharsets

def flowFile = session.get()
if (!flowFile) return

// Récupère les attributs nécessaires
def jsonPayload = flowFile.getAttribute('json.payload') ?: '{}'
def xmlFilename = flowFile.getAttribute('filename') ?: 'invoice.xml'

// Génère une boundary unique
def boundary = "----NiFiBoundary" + System.currentTimeMillis()

// Lit le contenu XML actuel du FlowFile
def xmlContent = new ByteArrayOutputStream()
session.read(flowFile, { inputStream ->
    IOUtils.copy(inputStream, xmlContent)
} as InputStreamCallback)

// Construit le corps multipart
def body = new ByteArrayOutputStream()

// --- Partie 1 : json (en premier) ---
body.write((
    "--${boundary}\r\n" +
    "Content-Disposition: form-data; name=\"json\"\r\n" +
    "Content-Type: application/json\r\n\r\n" +
    "${jsonPayload}\r\n"
).getBytes(StandardCharsets.UTF_8))

// --- Partie 2 : file (XML) ---
body.write((
    "--${boundary}\r\n" +
    "Content-Disposition: form-data; name=\"file\"; filename=\"${xmlFilename}\"\r\n" +
    "Content-Type: application/xml\r\n\r\n"
).getBytes(StandardCharsets.UTF_8))

body.write(xmlContent.toByteArray())
body.write("\r\n".getBytes(StandardCharsets.UTF_8))

// --- Fin du multipart ---
body.write("--${boundary}--\r\n".getBytes(StandardCharsets.UTF_8))

// Écrit le nouveau contenu dans le FlowFile
flowFile = session.write(flowFile, { outputStream ->
    outputStream.write(body.toByteArray())
} as OutputStreamCallback)

// Ajoute l'attribut boundary pour l'utiliser dans le header Content-Type d'InvokeHTTP
flowFile = session.putAttribute(flowFile, "multipart.boundary", boundary)

session.transfer(flowFile, REL_SUCCESS)
