package org.raml.jaxrs.generator.v10.types;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import org.raml.jaxrs.generator.CurrentBuild;
import org.raml.jaxrs.generator.GObjectType;
import org.raml.jaxrs.generator.SchemaTypeFactory;
import org.raml.jaxrs.generator.v10.TypeUtils;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.api.model.v10.datamodel.XMLTypeDeclaration;

/**
 * Created by Jean-Philippe Belanger on 1/3/17.
 * Just potential zeroes and ones
 */
public class V10GTypeXml extends V10GTypeHelper {

    private final XMLTypeDeclaration typeDeclaration;
    private final String name;
    private final String defaultJavatypeName;

    private TypeName modelSpecifiedJavaType;

    V10GTypeXml(XMLTypeDeclaration typeDeclaration, String realName, String defaultJavatypeName) {
        super(realName);
        this.typeDeclaration = typeDeclaration;
        this.name = realName;
        this.defaultJavatypeName = defaultJavatypeName;
    }

    @Override
    public TypeDeclaration implementation() {
        return typeDeclaration;
    }

    @Override
    public String type() {
        return typeDeclaration.type();
    }

    @Override
    public String name() {

        return name;
    }

    @Override
    public boolean isXml() {

        return true;
    }

    @Override
    public String schema() {

        return typeDeclaration.schemaContent();
    }


    @Override
    public TypeName defaultJavaTypeName(String pack) {

        if ( modelSpecifiedJavaType != null ) {

            return modelSpecifiedJavaType;
        }

        if ( isInline() ) {
            return ClassName.get("", defaultJavatypeName);
        } else {
            return ClassName.get(pack, defaultJavatypeName);
        }
    }

    @Override
    public void setJavaType(TypeName generatedJavaType) {

        this.modelSpecifiedJavaType = generatedJavaType;
    }

    public ClassName javaImplementationName(String pack) {

        return null;
    }

    public boolean isInline() {
        return TypeUtils.shouldCreateNewClass(typeDeclaration, typeDeclaration.parentTypes().toArray(new TypeDeclaration[0]));
    }

    @Override
    public String toString() {
        return "V10GTypeXml{" +
                "input=" + typeDeclaration.name() + ":" + typeDeclaration.type()+
                ", name='" + name() + '\'' +
                '}';
    }


    @Override
    public void construct(final CurrentBuild currentBuild, GObjectType objectType) {
        objectType.dispatch(new GObjectType.GObjectTypeDispatcher() {

            @Override
            public void onPlainObject() {

            }

            @Override
            public void onXmlObject() {

                SchemaTypeFactory.createXmlType(currentBuild, V10GTypeXml.this);
            }

            @Override
            public void onJsonObject() {

            }

            @Override
            public void onEnumeration() {

            }

            @Override
            public void onUnion() {

            }
        });
    }


}
