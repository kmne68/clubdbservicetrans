
package com.kemery;

/**
 *
 * @author kmne6
 */
public class Member {
    
    private String memid, lastnm, firstnm, middlenm, status, memdt, expdt;
    private long password, passattempt;
    private double ytd_total;
    private String ytd_total_dt;
    
    public Member() {
        
        this.memid = "";
        this.lastnm = "";
        this.firstnm = "";
        this.middlenm = "";
        this.status = "";
        this.memdt = "";
        this.ytd_total = 0.0;
        this.ytd_total_dt = "";
        this.password = 0;
        this.passattempt = 1;
        
    }
    
    
    public boolean isAuthenticated() {
        
        if(this.password > 0) {
        	if(this.password == this.passattempt) {
        		return true;
        	}
        }
        return false;

    }

    public String getMemid() {
        return memid;
    }

    public void setMemid(String memid) {
        this.memid = memid;
    }

    public String getLastnm() {
        return lastnm;
    }

    public void setLastnm(String lastnm) {
        this.lastnm = lastnm;
    }

    public String getFirstnm() {
        return firstnm;
    }

    public void setFirstnm(String firstnm) {
        this.firstnm = firstnm;
    }

    public String getMiddlenm() {
        return middlenm;
    }

    public void setMiddlenm(String middlenm) {
        this.middlenm = middlenm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemdt() {
        return memdt;
    }

    public void setMemdt(String memdt) {
        this.memdt = memdt;
    }
    
	
	public String getExpdt() {
		return this.expdt;
	}	
	
	public void setExpdt(String expdt) {
		this.expdt = expdt;
	}
	
    public long getPassword() {
        return password;
    }

    public void setPassword(long password) {
        this.password = password;
    }

    public long getPassattempt() {
        return passattempt;
    }

    public void setPassattempt(long passattempt) {
        this.passattempt = passattempt;
    }
    
    
    public void renew() {
    	int yr = Integer.parseInt(this.expdt.substring(0, 4));
    	yr++;
    	String newexpdt = String.valueOf(yr) + this.expdt.substring(4);
    	this.expdt = newexpdt;
    }
    
    
    
    @Override
    public String toString() {
    	return this.memid + ", " + this.lastnm + ", " + this.firstnm + ", " + this.middlenm + ", " + this.status + ", " + this.memdt + ", " + this.expdt + ", " + this.password;
    }


	public double getYtd_total() {
		return ytd_total;
	}


	public void setYtd_total(double ytd_total) {
		this.ytd_total = ytd_total;
	}


	public String getYtd_total_dt() {
		return ytd_total_dt;
	}


	public void setYtd_total_dt(String ytd_total_dt) {
		this.ytd_total_dt = ytd_total_dt;
	}
    
}
