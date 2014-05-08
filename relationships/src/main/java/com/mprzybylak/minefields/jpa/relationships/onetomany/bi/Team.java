package com.mprzybylak.minefields.jpa.relationships.onetomany.bi;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Team {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private long id;
	
	@OneToMany(mappedBy="team")
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
