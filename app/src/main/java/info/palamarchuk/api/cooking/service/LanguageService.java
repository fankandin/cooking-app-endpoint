package info.palamarchuk.api.cooking.service;

import info.palamarchuk.api.cooking.dao.GenericDao;
import info.palamarchuk.api.cooking.entity.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LanguageService {

    GenericDao<Language> dao;

    @Autowired
    public LanguageService(GenericDao<Language> dao) {
        this.dao = dao;
        this.dao.setClazz(Language.class);
    }

    public List<Language> getAll() {
        return dao.findAll();
    }

    public Language getById(long id) {
        return dao.findOne((short)id);
    }

}
