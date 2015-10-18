package com.ujujzk.easyengmaterial.eeapp.dao.parse;

import com.ujujzk.easyengmaterial.eeapp.model.Base;

public class ParseLocalCrudDaoImpl<Model extends Base> extends AbstractParseCrudDaoImpl<Model> {

    public ParseLocalCrudDaoImpl(Class clazz) {
        super(false, clazz);
    }
}
