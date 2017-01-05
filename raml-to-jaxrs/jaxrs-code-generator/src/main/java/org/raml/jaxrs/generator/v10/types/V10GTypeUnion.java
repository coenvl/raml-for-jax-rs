package org.raml.jaxrs.generator.v10.types;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import org.raml.jaxrs.generator.CurrentBuild;
import org.raml.jaxrs.generator.GObjectType;
import org.raml.jaxrs.generator.v10.Annotations;
import org.raml.jaxrs.generator.v10.TypeUtils;
import org.raml.jaxrs.generator.v10.V10TypeRegistry;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.api.model.v10.datamodel.UnionTypeDeclaration;

/**
 * Created by Jean-Philippe Belanger on 1/3/17.
 * Just potential zeroes and ones
 */
public class V10GTypeUnion extends V10GTypeHelper {

    private final V10TypeRegistry registry;
    private final TypeDeclaration typeDeclaration;
    private final String name;
    private final String defaultJavatypeName;


    V10GTypeUnion(V10TypeRegistry registry, UnionTypeDeclaration typeDeclaration, String realName, String defaultJavatypeName) {
        super(realName);
        this.registry = registry;
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
    public boolean isUnion() {

        return true;
    }

    @Override
    public TypeName defaultJavaTypeName(String pack) {

        if ( isInline() ) {
            return ClassName.get("", defaultJavatypeName);
        } else {
            return ClassName.get(pack, defaultJavatypeName);
        }
    }

    public ClassName javaImplementationName(String pack) {

        if ( isInline() ) {

            return ClassName
                    .get("", Annotations.IMPLEMENTATION_CLASS_NAME.get(typeDeclaration, defaultJavatypeName + "Impl"));
        } else {
            return ClassName
                    .get(pack, Annotations.IMPLEMENTATION_CLASS_NAME.get(typeDeclaration, defaultJavatypeName + "Impl"));
        }
    }

    public boolean isInline() {
        return TypeUtils.shouldCreateNewClass(typeDeclaration, typeDeclaration.parentTypes().toArray(new TypeDeclaration[0]));
    }


    @Override
    public String toString() {
        return "V10GTypeUnion{" +
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

            }

            @Override
            public void onJsonObject() {

            }

            @Override
            public void onEnumeration() {

            }

            @Override
            public void onUnion() {
                V10TypeFactory.createUnion(currentBuild, registry, V10GTypeUnion.this);
            }
        });
    }


}
