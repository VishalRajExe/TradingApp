package com.vishal.model;

import com.vishal.domain.VerificationType;

import lombok.Data;

@Data
public class TwoFactorAuth {
		private boolean isEnabled = false;
		private VerificationType sendTo;
	
}
