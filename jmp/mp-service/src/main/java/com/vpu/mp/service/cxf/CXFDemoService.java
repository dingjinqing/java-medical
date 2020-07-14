package com.vpu.mp.service.cxf;

/**
 * @author 赵晓东
 * @description webService传参接口
 * @create 2020-07-14 18:14
 */

import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * @WebResult 定义返回值，在方法返回值前边
 * name（String，非必须）：返回结果值的名称
 * partName（String，非必须）：
 * header（String，非必须）：
 */

/**
 *
 * @WebService 服务名配置
 * name（String，非必需）：webservice的名字。映射为WSDL文件中的<wsdl:portType>元素。在JWS文件中的默认值是Java class的非全路径名称。
 * targetNamespace（String，非必需）：从这个webservice生成的WSDL和XML元素的XML命名空间。默认值是通过JAX-RPC规范指定的。
 * serviceName（String，非必需）：这个webservice的service名字。映射为WSDL文件中的<wsdl:service>元素。默认值事Java class在JWS文件中的非全路径名称，再加上Service这个字符串。
 * wsdlLocation（String，非必需）：一个pre-defined WSDL文件的相对或绝对URL。如果你指定了这个属性，那么jwscAnt任务将不生成WSDL文件，并且会返回一个错误，如果JWS文件和WSDL文件中的port type和bindings不一致的话。（注意：wsdlcAnt任务在从一个WSDL生成endpoint接口JWS文件的时候使用这个属性。通常来说，用户从来不会使用这个属性在他们自己的JWS文件中）
 * endpointInterface（String，非必需）：已经存在的service endpoint接口文件的全名。如果你指定了这个属性，那就意味着你已经创建了endpoint接口文件，并且它存在于你的CLASSPATH下。
 * portName(String, 非必须)：服务发布默认端口名称
 */

/**
 *
 * @WebMethod 服务方法配置
 * operationName（String，非必需）：operation的名字。映射为WSDL文件中的<wsdl:operation>元素。默认值事这个方法的名称。
 * action（String，非必需）：这个operation的action。对于SOAP绑定来说，这个属性的值决定了在SOAP消息中的SOAPAction header的值。
 * exclude（boolean，非必须）：默认false，是否被忽略。
 */

/**
 *
 * @WebParam 作用于Parameter。在webservice的输入参数的operation和生成的WSDL文件的元素之间自定义映射。也被用来指定参数的行为。
 * name（String，非必需）：在WSDL文件中的参数名称。对于RPC风格的webservice来说，name映射为<wsdl:part>元素的名称来代表这个参数。对于document风格的webservice来说，name是XML元素的local name来代表这个参数。默认值是方法参数的名字。
 * targetNamespace（String，非必需）：参数的XML命名空间。这个值只在document风格的webservice使用，从而参数映射为XML元素。默认值是这个webservice的targetNamespace。
 * mode（enum，非必需）：参数流动的方向。合法的值是：WebParam.Mode.IN，WebParam.Mode.OUT，WebParam.Mode.INOUT。默认的值是WebParam.Mode.IN。如果你指定了WebParam.Mode.OUT或WebParam.Mode.INOUT，那么参数的数据类型必须是Holder或者Holder的扩展。详细信息，请看JAX-RPC规范。
 * WebParam.Mode.OUT和WebParam.Mode.INOUT模式只支持RPC风格的webservice，或者是映射到header上的参数。
 * header（boolean，非必需）：指定在SOAP header上可以找到该参数的值。默认情况下，参数是在SOAP body中的，合法的值是true和false。默认值是false。
 * partName(String, 非必须)
 */

@WebService(name = "CommonService", // 暴露服务名称
    targetNamespace = "http://service.zsxj.com/"// 命名空间,一般是接口的包名倒序
)
@Service
public interface CXFDemoService {
    void demo();
}
