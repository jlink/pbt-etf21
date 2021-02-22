package pbt.examples.projectmgt;

import net.jqwik.api.*;

import static org.assertj.core.api.Assertions.*;

class ProjectTests {

	@Example
	void can_add_many_team_members_to_a_project() {
		Project project = new Project("My big project");

		User alex = new User("alex@example.com");
		User kim = new User("kim@example.com");
		User pat = new User("pat@example.com");
		project.addMember(alex);
		project.addMember(kim);
		project.addMember(pat);

		assertThat(project.isMember(alex)).isTrue();
		assertThat(project.isMember(kim)).isTrue();
		assertThat(project.isMember(pat)).isTrue();
	}
}
