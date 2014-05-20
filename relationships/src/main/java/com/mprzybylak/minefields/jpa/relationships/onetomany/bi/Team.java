package com.mprzybylak.minefields.jpa.relationships.onetomany.bi;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Team {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private long id;
	
	
	@OneToMany(
			mappedBy="team",
			fetch = FetchType.LAZY // lazy fetch by default but it can be changed by "fetch" parameter
			)
	Collection<Member> members = new ArrayList<>();
	
	public void addMember(Member member) {
		members.add(member);
	}
	
	public Collection<Member> getMembers() {
		return members;
	}

	public long getId() {
		return id;
	}

}
