package org.kairos.ibpnh.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.kairos.ibpnh.vo.user.RoleFunctionVo;
import org.kairos.ibpnh.vo.user.RoleTypeFunctionVo;
import org.kairos.ibpnh.vo.user.UserVo;
import org.springframework.beans.factory.FactoryBean;

/**
 * Factory Bean for Gson Serializer/Deserializer
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
 *
 */
public class GsonSpringFactoryBean implements FactoryBean<Gson> {

    /**
     * Singleton Instance.
     */
    private Gson singleton = null;

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    @Override
    public Gson getObject() throws Exception {
        if (this.singleton == null) {
            // we create the GsonBuilder
            GsonBuilder gsb = new GsonBuilder();

            gsb.setDateFormat("dd/MM/yyyy HH:mm:ss.SSS");

            // type adapters registration
//            gsb.registerTypeAdapter(BigDecimal.class,
//                    new BigDecimalTypeAdapter(this.bigDecimalUtils));


            // exclude password from being exposed to the client
            gsb.addSerializationExclusionStrategy(new CustomExclusionStrategy(UserVo.class, false, "password"));
            gsb.addSerializationExclusionStrategy(new CustomExclusionStrategy(RoleTypeFunctionVo.class, false, "roleType"));
            gsb.addSerializationExclusionStrategy(new CustomExclusionStrategy(RoleFunctionVo.class, false, "role"));

            // point of sale circular reference avoidance
//            gsb.addSerializationExclusionStrategy(new CustomExclusionStrategy(
//                    PointOfSaleUserVo.class, false, "pointOfSale"));


            // serializes complex map keys
            gsb.enableComplexMapKeySerialization();

            // returns the created builder
            this.singleton = gsb.create();
        }

        // returns the singleton
        return this.singleton;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    @Override
    public Class<?> getObjectType() {
        return Gson.class;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    @Override
    public boolean isSingleton() {
        return true;
    }

}

