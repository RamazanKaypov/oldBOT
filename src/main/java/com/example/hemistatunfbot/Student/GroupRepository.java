package com.example.hemistatunfbot.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "group")
public interface GroupRepository extends JpaRepository<Group,Long> {
    boolean existsByGroupName(String groupName);

    Group findByGroupName(String string);

    boolean existsByGroupNameAndIdNot(String groupName, Long id);
}
