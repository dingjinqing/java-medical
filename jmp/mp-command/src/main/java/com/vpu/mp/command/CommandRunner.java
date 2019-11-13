package com.vpu.mp.command;

import java.util.List;

import org.springframework.boot.ApplicationArguments;

import com.vpu.mp.service.foundation.util.Util;

/**
 * 
 * @author lixinguo
 *
 */
public interface CommandRunner {

	String description();

	void run(ApplicationArguments args);
	
	
	default String getNonOption(ApplicationArguments args,Integer pos) {
		return getNonOption(args, pos, String.class, null);
	}
	
	default <T> T getNonOption(ApplicationArguments args, Integer pos, Class<? extends T> cls) {
		return getNonOption(args, pos, cls, null);
	}

	default <T> T getNonOption(ApplicationArguments args, Integer pos, Class<? extends T> cls, T defaultValue) {
		List<String> options = args.getNonOptionArgs();
		if (options != null && options.size() > pos) {
			return Util.convert(options.get(pos), cls, defaultValue);
		}
		return defaultValue;
	}
	
	default boolean hasOption(ApplicationArguments args, String name) {
		return  args.getOptionValues(name) != null;
	}
	
	default String getOption(ApplicationArguments args, String name) {
		return getOption(args, name, String.class, null);
	}
	
	default <T> T getOption(ApplicationArguments args, String name, Class<? extends T> cls) {
		return getOption(args, name, cls, null);
	}

	default <T> T getOption(ApplicationArguments args, String name, Class<? extends T> cls, T defaultValue) {
		List<String> options = args.getOptionValues(name);
		if (options != null && options.size() > 0) {
			return Util.convert(options.get(0), cls, defaultValue);
		}
		return defaultValue;
	}

	
}
