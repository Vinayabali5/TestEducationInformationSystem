package uk.ac.reigate.repositories.learning_support

import org.springframework.data.repository.CrudRepository

import uk.ac.reigate.domain.learning_support.SupportType

interface SupportTypeRepository extends CrudRepository<SupportType, Integer> {
}
