package gov.nih.nci.cadsr.umlproject.domain;

import java.util.Collection;import gov.nih.nci.cadsr.domain.ObjectClass;
import java.io.Serializable;
	/**
	* caDSR properties of the UML Class in a domain model.	**/
public class UMLClassMetadata  implements Serializable
{
	/**
	* An attribute to allow serialization of the domain objects
	*/
	private static final long serialVersionUID = 1234567890L;

	
		/**
	* Text pertaining to the UML class.	**/
	public String description;
	/**
	* Retreives the value of description attribute
	* @return description
	**/

	public String getDescription(){
		return description;
	}

	/**
	* Sets the value of description attribue
	**/

	public void setDescription(String description){
		this.description = description;
	}
	
		/**
	* The caDSR Object Class alternate name that is equal to the UML fully qualified package name; all the folders in the path.  This is stored in the Alternate Names of type = UML CLASS and is classified by the Project (CS/CSI)	**/
	public String fullyQualifiedName;
	/**
	* Retreives the value of fullyQualifiedName attribute
	* @return fullyQualifiedName
	**/

	public String getFullyQualifiedName(){
		return fullyQualifiedName;
	}

	/**
	* Sets the value of fullyQualifiedName attribue
	**/

	public void setFullyQualifiedName(String fullyQualifiedName){
		this.fullyQualifiedName = fullyQualifiedName;
	}
	
		/**
	* Used to represent the XMLNamespace from the GME	**/
	private String gmeNamespace;
	/**
	* Retreives the value of gmeNamespace attribute
	* @return gmeNamespace
	**/

	public String getGmeNamespace(){
		return gmeNamespace;
	}

	/**
	* Sets the value of gmeNamespace attribue
	**/

	public void setGmeNamespace(String gmeNamespace){
		this.gmeNamespace = gmeNamespace;
	}
	
		/**
	* Used to identify XML Element in GME	**/
	private String gmeXMLElement;
	/**
	* Retreives the value of gmeXMLElement attribute
	* @return gmeXMLElement
	**/

	public String getGmeXMLElement(){
		return gmeXMLElement;
	}

	/**
	* Sets the value of gmeXMLElement attribue
	**/

	public void setGmeXMLElement(String gmeXMLElement){
		this.gmeXMLElement = gmeXMLElement;
	}
	
		/**
	* The 36 character caDSR database identifier.	**/
	public String id;
	/**
	* Retreives the value of id attribute
	* @return id
	**/

	public String getId(){
		return id;
	}

	/**
	* Sets the value of id attribue
	**/

	public void setId(String id){
		this.id = id;
	}
	
		/**
	* The words by which the attribute is known in the object class is known.	**/
	public String name;
	/**
	* Retreives the value of name attribute
	* @return name
	**/

	public String getName(){
		return name;
	}

	/**
	* Sets the value of name attribue
	**/

	public void setName(String name){
		this.name = name;
	}
	
	/**
	* An associated gov.nih.nci.cadsr.umlproject.domain.SemanticMetadata object's collection 
	**/
			
	private Collection<SemanticMetadata> semanticMetadataCollection;
	/**
	* Retreives the value of semanticMetadataCollection attribue
	* @return semanticMetadataCollection
	**/

	public Collection<SemanticMetadata> getSemanticMetadataCollection(){
		return semanticMetadataCollection;
	}

	/**
	* Sets the value of semanticMetadataCollection attribue
	**/

	public void setSemanticMetadataCollection(Collection<SemanticMetadata> semanticMetadataCollection){
		this.semanticMetadataCollection = semanticMetadataCollection;
	}
		
	/**
	* An associated gov.nih.nci.cadsr.umlproject.domain.UMLGeneralizationMetadata object
	**/
			
	private UMLGeneralizationMetadata UMLGeneralizationMetadata;
	/**
	* Retreives the value of UMLGeneralizationMetadata attribue
	* @return UMLGeneralizationMetadata
	**/
	
	public UMLGeneralizationMetadata getUMLGeneralizationMetadata(){
		return UMLGeneralizationMetadata;
	}
	/**
	* Sets the value of UMLGeneralizationMetadata attribue
	**/

	public void setUMLGeneralizationMetadata(UMLGeneralizationMetadata UMLGeneralizationMetadata){
		this.UMLGeneralizationMetadata = UMLGeneralizationMetadata;
	}
			
	/**
	* An associated gov.nih.nci.cadsr.umlproject.domain.UMLAttributeMetadata object's collection 
	**/
			
	private Collection<UMLAttributeMetadata> UMLAttributeMetadataCollection;
	/**
	* Retreives the value of UMLAttributeMetadataCollection attribue
	* @return UMLAttributeMetadataCollection
	**/

	public Collection<UMLAttributeMetadata> getUMLAttributeMetadataCollection(){
		return UMLAttributeMetadataCollection;
	}

	/**
	* Sets the value of UMLAttributeMetadataCollection attribue
	**/

	public void setUMLAttributeMetadataCollection(Collection<UMLAttributeMetadata> UMLAttributeMetadataCollection){
		this.UMLAttributeMetadataCollection = UMLAttributeMetadataCollection;
	}
		
	/**
	* An associated gov.nih.nci.cadsr.domain.ObjectClass object
	**/
			
	private ObjectClass objectClass;
	/**
	* Retreives the value of objectClass attribue
	* @return objectClass
	**/
	
	public ObjectClass getObjectClass(){
		return objectClass;
	}
	/**
	* Sets the value of objectClass attribue
	**/

	public void setObjectClass(ObjectClass objectClass){
		this.objectClass = objectClass;
	}
			
	/**
	* An associated gov.nih.nci.cadsr.umlproject.domain.UMLPackageMetadata object
	**/
			
	private UMLPackageMetadata UMLPackageMetadata;
	/**
	* Retreives the value of UMLPackageMetadata attribue
	* @return UMLPackageMetadata
	**/
	
	public UMLPackageMetadata getUMLPackageMetadata(){
		return UMLPackageMetadata;
	}
	/**
	* Sets the value of UMLPackageMetadata attribue
	**/

	public void setUMLPackageMetadata(UMLPackageMetadata UMLPackageMetadata){
		this.UMLPackageMetadata = UMLPackageMetadata;
	}
			
	/**
	* An associated gov.nih.nci.cadsr.umlproject.domain.UMLAssociationMetadata object's collection 
	**/
			
	private Collection<UMLAssociationMetadata> UMLAssociationMetadataCollection;
	/**
	* Retreives the value of UMLAssociationMetadataCollection attribue
	* @return UMLAssociationMetadataCollection
	**/

	public Collection<UMLAssociationMetadata> getUMLAssociationMetadataCollection(){
		return UMLAssociationMetadataCollection;
	}

	/**
	* Sets the value of UMLAssociationMetadataCollection attribue
	**/

	public void setUMLAssociationMetadataCollection(Collection<UMLAssociationMetadata> UMLAssociationMetadataCollection){
		this.UMLAssociationMetadataCollection = UMLAssociationMetadataCollection;
	}
		
	/**
	* An associated gov.nih.nci.cadsr.umlproject.domain.Project object
	**/
			
	private Project project;
	/**
	* Retreives the value of project attribue
	* @return project
	**/
	
	public Project getProject(){
		return project;
	}
	/**
	* Sets the value of project attribue
	**/

	public void setProject(Project project){
		this.project = project;
	}
			
	/**
	* Compares <code>obj</code> to it self and returns true if they both are same
	*
	* @param obj
	**/
	public boolean equals(Object obj)
	{
		if(obj instanceof UMLClassMetadata) 
		{
			UMLClassMetadata c =(UMLClassMetadata)obj; 			 
			if(getId() != null && getId().equals(c.getId()))
				return true;
		}
		return false;
	}
		
	/**
	* Returns hash code for the primary key of the object
	**/
	public int hashCode()
	{
		if(getId() != null)
			return getId().hashCode();
		return 0;
	}
	
}