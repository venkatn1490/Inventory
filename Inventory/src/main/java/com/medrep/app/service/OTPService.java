package com.medrep.app.service;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medrep.app.dao.OTPDAO;
import com.medrep.app.entity.OTPEntity;
import com.medrep.app.model.OTP;
import com.medrep.app.util.OTPUtil;
import com.medrep.app.util.PasswordProtector;

@Service("otpService")
@Transactional
public class OTPService 
{
		
	@Autowired
	OTPDAO otpdao;
	
	private static final Log log = LogFactory.getLog(OTPService.class);
	
	public OTP createOTP(OTP otp)
	{
		
			OTPEntity otpEntity = new OTPEntity();
			otpEntity.setSecurityId(otp.getSecurityId());
			otpEntity.setVerificationId(otp.getVerificationId());
			otpEntity.setType(otp.getType());
			Calendar calendar = Calendar.getInstance();
			if("EMAIL".equals(otp.getType()))
			{
				calendar.add(Calendar.MONTH, 1);
				otpEntity.setValidUpto(new Date(calendar.getTimeInMillis()));
				otp.setOtp(PasswordProtector.encrypt(OTPUtil.generateToken()));
				otpEntity.setOtp(otp.getOtp());

				//TODO Send Email Notification to user
			}
			else
			{
				calendar.add(Calendar.MINUTE, 30);
				otpEntity.setValidUpto(new Date(calendar.getTimeInMillis()));
				otp.setOtp(PasswordProtector.encrypt(OTPUtil.generateIntToken()));
				otpEntity.setOtp(otp.getOtp());

				//TODO send SMS Verification to user
			}
			otpEntity.setStatus("WAITING");
			otpdao.persist(otpEntity);
			
		return otp;
	}
	
	public OTP verifyOTP(OTP otp)
	{
			OTPEntity otpEntity = otpdao.findByVerificationId(otp.getVerificationId());
		
			if(otpEntity != null && "WAITING".equals(otpEntity.getStatus()))
			{
				if(otp.getType().equals(otpEntity.getType()) && PasswordProtector.encrypt(otp.getOtp()).equals(otpEntity.getOtp()))
				{
					if(otpEntity.getValidUpto().getTime() > System.currentTimeMillis())
					{
						otp.setStatus("VERIFIED");
						
					}
					else
					{
						otp.setStatus("EXPIRED");
					}
					
					otpEntity.setStatus(otp.getStatus());
					otpdao.merge(otpEntity);
				}
				else
				{
					otp.setStatus("INVALID");
				}
			}
			else
			{
				otp.setStatus("INVALID");
			}
		
		return otp;
	}
	
	public OTP reCreateOTP(OTP otp)
	{
		
			OTPEntity otpEntity = otpdao.findByVerificationId(otp.getVerificationId());
			if(otpEntity !=null && otpEntity.getOtpId() != null)
			{
				
				otpEntity.setVerificationId(otp.getVerificationId());
				otpEntity.setType(otp.getType());
				Calendar calendar = Calendar.getInstance();
				if("EMAIL".equals(otp.getType()))
				{
					calendar.add(Calendar.MONTH, 1);
					otpEntity.setValidUpto(new Date(calendar.getTimeInMillis()));
					otp.setOtp(PasswordProtector.encrypt(OTPUtil.generateToken()));
					otpEntity.setOtp(otp.getOtp());

					//TODO Send Email Notification to user
				}
				else
				{
					calendar.add(Calendar.MINUTE, 30);
					otpEntity.setValidUpto(new Date(calendar.getTimeInMillis()));
					otp.setOtp(PasswordProtector.encrypt(OTPUtil.generateIntToken()));
					otpEntity.setOtp(otp.getOtp());

					//TODO send SMS Verification to user
				}
				otpEntity.setStatus("WAITING");
				otpdao.merge(otpEntity);
			}
			else
			{
				otp = createOTP(otp);
			}
			
			
		return otp;
	}
	
	public OTP reCreateOTPPatient(OTP otp)
	{
		
			OTPEntity otpEntity = otpdao.findByVerificationId(otp.getVerificationId());
			if(otpEntity !=null && otpEntity.getOtpId() != null)
			{
				
				otpEntity.setVerificationId(otp.getVerificationId());
				otpEntity.setType(otp.getType());
				Calendar calendar = Calendar.getInstance();
				if("EMAIL".equals(otp.getType()))
				{
					calendar.add(Calendar.MONTH, 1);
					otpEntity.setValidUpto(new Date(calendar.getTimeInMillis()));
					otp.setOtp(PasswordProtector.encrypt(OTPUtil.generateToken()));
					otpEntity.setOtp(otp.getOtp());

					//TODO Send Email Notification to user
				}
				else
				{
					calendar.add(Calendar.MINUTE, 30);
					otpEntity.setValidUpto(new Date(calendar.getTimeInMillis()));
					otp.setOtp(PasswordProtector.encrypt(OTPUtil.generateIntToken()));
					otpEntity.setOtp(otp.getOtp());

					//TODO send SMS Verification to user
				}
				otpEntity.setStatus("WAITING");
				otpdao.merge(otpEntity);
			}
			else
			{
				otp = createOTP(otp);
			}
			
			
		return otp;
	}
	
	public OTP getOTP(OTP otp)
	{
			System.out.println("*********************" + otp.getVerificationId());
			OTPEntity otpEntity = otpdao.findByVerificationId(otp.getVerificationId());
			if(otpEntity != null)
			{
				otp.setOtp(PasswordProtector.decrypt(otpEntity.getOtp()));
				otp.setType(otpEntity.getType());
				otp.setStatus(otpEntity.getStatus());
			}
			return otp;
	}

}
