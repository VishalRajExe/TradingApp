package com.vishal.services;

import com.vishal.model.TwoFactorOTP;
import com.vishal.model.User;
public interface TwoFactorOtpService {
	 
	TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt) ;
	
	TwoFactorOTP findByUser(Long userId);
	
	public TwoFactorOTP findById(String id);
	
	boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp, String otp);
	
	void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP);
}
