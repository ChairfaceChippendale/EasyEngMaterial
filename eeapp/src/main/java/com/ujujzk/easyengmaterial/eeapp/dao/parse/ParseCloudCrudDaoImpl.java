package com.ujujzk.easyengmaterial.eeapp.dao.parse;

import com.ujujzk.easyengmaterial.eeapp.model.Base;

public class ParseCloudCrudDaoImpl<Model extends Base> extends AbstractParseCrudDaoImpl<Model> {

    public ParseCloudCrudDaoImpl(Class clazz) {
        super(true, clazz);
    }
}
