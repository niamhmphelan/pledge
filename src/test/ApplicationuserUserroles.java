// default package
// Generated Jul 14, 2016 11:10:17 PM by Hibernate Tools 5.1.0.Alpha1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ApplicationuserUserroles generated by hbm2java
 */
@Entity
@Table(name = "APPLICATIONUSER_USERROLES", catalog = "netgrains")
public class ApplicationuserUserroles implements java.io.Serializable {

	private ApplicationuserUserrolesId id;

	public ApplicationuserUserroles() {
	}

	public ApplicationuserUserroles(ApplicationuserUserrolesId id) {
		this.id = id;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "applicationuserid", column = @Column(name = "APPLICATIONUSERID", nullable = false)),
			@AttributeOverride(name = "referenceid", column = @Column(name = "REFERENCEID")) })
	public ApplicationuserUserrolesId getId() {
		return this.id;
	}

	public void setId(ApplicationuserUserrolesId id) {
		this.id = id;
	}

}