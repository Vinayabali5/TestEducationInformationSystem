package uk.ac.reigate.domain.staff

import javax.persistence.AttributeOverride
import javax.persistence.AttributeOverrides
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate

import groovy.transform.EqualsAndHashCode
import uk.ac.reigate.domain.BaseEntity

@Entity
@Table(name = "disability", schema = "Staff")
@AttributeOverrides([
    @AttributeOverride(name = "id", column = @Column(name = "disability_id"))
])
@DynamicInsert(true)
@DynamicUpdate(true)
@EqualsAndHashCode(includeFields=true)
class Disability extends BaseEntity implements Serializable {
    
    @Column(name = "disability")
    String disability
    
    Disability(){}
}

