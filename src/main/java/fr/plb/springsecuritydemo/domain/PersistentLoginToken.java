package fr.plb.springsecuritydemo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Document(collection = "persistent_token")
public class PersistentLoginToken implements Serializable {
		
	@Id
	private String id;
	private final String username;
	private final String series;
	private final String tokenValue;
	private final Date date;

	@PersistenceConstructor
	public PersistentLoginToken(String id, String username, String series, String tokenValue, Date date) {
		this.id = id;
		this.username=username;
		this.series = series;
		this.tokenValue = tokenValue;
		this.date = date;
	}

	@PersistenceConstructor
	public PersistentLoginToken(String username, String series, String tokenValue, Date date) {
		this.username=username;
		this.series = series;
		this.tokenValue = tokenValue;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public String getSeries() {
		return series;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	public Date getDate() {
		return date;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PersistentLoginToken that = (PersistentLoginToken) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "PersistentLoginToken{" +
				"id='" + id + '\'' +
				", username='" + username + '\'' +
				", series='" + series + '\'' +
				", tokenValue='" + tokenValue + '\'' +
				", date=" + date +
				'}';
	}
}
