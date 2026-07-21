<soap:Body>
    <ctyp:ExecuteChangesRequest refresh="true">

      <!-- ChangeRequest 1 : création du document (identique à avant) -->
      <ctyp:ChangeRequest id="1">
        <ctyp:TargetSpecification xsi:type="ctyp:ObjectStoreSpecification"
             objectStore="${xmlEscape(osSymbolic)}"/>

        <ctyp:Action xsi:type="ctyp:CreateAction"
             classId="${xmlEscape(docClass)}"
             autoUniqueContainmentName="0"/>

        <ctyp:Action xsi:type="ctyp:CheckinAction"
             autoClassify="false"
             checkinMinorVersion="false"
             definesSecurityParentage="1"/>

        <ctyp:ActionProperties>
          <ctyp:Property xsi:type="ctyp:SingletonString" propertyId="DocumentTitle">
            <ctyp:Value>${xmlEscape(fileName)}</ctyp:Value>
          </ctyp:Property>
          <ctyp:Property xsi:type="ctyp:SingletonString" propertyId="MimeType">
            <ctyp:Value>${mimeType}</ctyp:Value>
          </ctyp:Property>
          <ctyp:Property xsi:type="ctyp:ListOfObject" propertyId="ContentElements" listMode="Replace">
            <ctyp:Value xsi:type="ctyp:ContentTransfer" classId="ContentTransfer" dependentAction="Insert">
              <ctyp:Property xsi:type="ctyp:SingletonString" propertyId="RetrievalName">
                <ctyp:Value>${xmlEscape(fileName)}</ctyp:Value>
              </ctyp:Property>
              <ctyp:Property xsi:type="ctyp:SingletonString" propertyId="ContentType">
                <ctyp:Value>${mimeType}</ctyp:Value>
              </ctyp:Property>
              <ctyp:Property xsi:type="ctyp:ContentData" propertyId="Content">
                <ctyp:Value xsi:type="ctyp:Binary">
                  <xop:Include href="cid:${contentPartId}"/>
                </ctyp:Value>
              </ctyp:Property>
            </ctyp:Value>
          </ctyp:Property>
        </ctyp:ActionProperties>
      </ctyp:ChangeRequest>

      <!-- ChangeRequest 2 : filer le document dans le dossier X -->
      <ctyp:ChangeRequest id="2">
        <ctyp:TargetSpecification xsi:type="ctyp:ObjectStoreSpecification"
             objectStore="${xmlEscape(osSymbolic)}"/>

        <ctyp:Action xsi:type="ctyp:CreateAction"
             classId="ReferentialContainmentRelationship"/>

        <ctyp:ActionProperties>
          <!-- Head = le dossier cible X, référencé par chemin ou par ID connu -->
          <ctyp:Property xsi:type="ctyp:SingletonObject" propertyId="Head">
            <ctyp:Value xsi:type="ctyp:ReferentialContainmentRelationship">
              <ctyp:SourceSpecification xsi:type="ctyp:PathBasedObjectSpecification"
                   classId="Folder"
                   path="${xmlEscape(targetFolderPath)}"/>
            </ctyp:Value>
          </ctyp:Property>

          <!-- Tail = le document créé au ChangeRequest id="1" (pas encore d'ID réel -> idRef) -->
          <ctyp:Property xsi:type="ctyp:SingletonObject" propertyId="Tail">
            <ctyp:Value xsi:type="${docClass}">
              <ctyp:SourceSpecification xsi:type="ctyp:DependentObjectSpecification"
                   idRef="1"/>
            </ctyp:Value>
          </ctyp:Property>

          <!-- nom d'affichage de l'objet dans ce dossier -->
          <ctyp:Property xsi:type="ctyp:SingletonString" propertyId="ContainmentName">
            <ctyp:Value>${xmlEscape(fileName)}</ctyp:Value>
          </ctyp:Property>
        </ctyp:ActionProperties>
      </ctyp:ChangeRequest>

    </ctyp:ExecuteChangesRequest>
  </soap:Body>
</soap:Envelope>
