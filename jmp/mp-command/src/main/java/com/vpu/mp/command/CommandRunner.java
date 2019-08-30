package com.vpu.mp.command;

import org.springframework.boot.ApplicationArguments;

public interface CommandRunner {

	String description();

	void run(ApplicationArguments args);
}
