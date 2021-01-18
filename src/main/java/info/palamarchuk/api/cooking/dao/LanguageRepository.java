package info.palamarchuk.api.cooking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import info.palamarchuk.api.cooking.entity.Language;

public interface LanguageRepository extends JpaRepository<Language, Short> {
}
