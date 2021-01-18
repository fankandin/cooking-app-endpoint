package info.palamarchuk.api.cooking.service;

import info.palamarchuk.api.cooking.dao.GenericDao;
import info.palamarchuk.api.cooking.dao.LanguageRepository;
import info.palamarchuk.api.cooking.entity.Language;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LanguageService {

    LanguageRepository repository;

    public List<Language> getAll() {
        return repository.findAll();
    }

    public Optional<Language> getById(long id) {
        if (id > Short.MAX_VALUE || id < 1) {
            return Optional.empty();
        }
        return repository.findById((short)id);
    }

}
