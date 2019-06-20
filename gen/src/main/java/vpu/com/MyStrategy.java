package vpu.com;

import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.meta.Definition;

public class MyStrategy extends DefaultGeneratorStrategy {


	/**
	 * Override this to specifiy what identifiers in Java should look like. This
	 * will just take the identifier as defined in the database.
	 */
	@Override
	public String getJavaIdentifier(Definition definition) {
		// The DefaultGeneratorStrategy disambiguates some synthetic object names,
		// such as the MySQL PRIMARY key names, which do not really have a name
		// Uncomment the below code if you want to reuse that logic.
		// if (definition instanceof IndexDefinition)
		// return super.getJavaIdentifier(definition);
		String outName = super.getJavaIdentifier(definition);
		if(outName.startsWith("B2C_")) {
			outName = outName.substring(4);
			System.out.println("getJavaIdentifier:" + outName);
		}
		return outName;
	}

	/**
	 * Override this method to re-define the package names of your generated
	 * artefacts.
	 */
	@Override
	public String getJavaPackageName(Definition definition, Mode mode) {
		String outName = super.getJavaPackageName(definition, mode);
//		System.out.println("getJavaPackageName:" + outName + ",Mode:" + mode);

		return outName;
	}

	@Override
	public String getJavaClassName(Definition definition, Mode mode) {
		String outName = super.getJavaClassName(definition, mode);
		if(outName.startsWith("B2c")){
			outName = outName.substring(3);
			System.out.println("getJavaClassName:" + outName + ",Mode:" + mode);
		}
	
		return outName;
//    	
//        StringBuilder result = new StringBuilder();
// 
//        String outputName = definition.getOutputName();
//        if (!StringUtils.isBlank(tablePrefix) && outputName.startsWith(tablePrefix)) {
//            outputName = outputName.substring(tablePrefix.length());
//        }
//        result.append(StringUtils.toCamelCase(outputName));
// 
//        if (mode == Mode.RECORD) {
//            result.append("Record");
//        } else if (mode == Mode.DAO) {
//            result.append("Dao");
//        } else if (mode == Mode.INTERFACE) {
//            result.insert(0, "I");
//        }
//        return result.toString();
	}

}
