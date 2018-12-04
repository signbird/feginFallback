package org.bqf.common.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * 使用 Fastjson 替换 Spring MVC 默认的 HttpMessageConverter，
 * 以提高 @RestController @ResponseBody @RequestBody 注解的 JSON序列化速度
 * 参考： https://github.com/alibaba/fastjson/wiki/%E5%9C%A8-Spring-%E4%B8%AD%E9%9B%86%E6%88%90-Fastjson
 * @author baiqiufei
 * 创建时间: 2017/10/23 10:30
 *
 */
@Configuration
public class FastjsonConfig extends WebMvcConfigurerAdapter {
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverter jsonConverter = new FastJsonHttpMessageConverter();
		List<MediaType> types = new ArrayList<>(2);
		types.add(MediaType.APPLICATION_JSON);
		types.add(MediaType.APPLICATION_JSON_UTF8);
		jsonConverter.setSupportedMediaTypes(types);

		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		// 防止过滤掉null字段
		fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
		jsonConverter.setFastJsonConfig(fastJsonConfig);
		converters.add(jsonConverter);
	}
	
	/**
     * 替换所有http转换(如Feign client)的序列化工具为FastJson
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters(){
        //1.需要定义一个convert转换消息的对象;
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //2:添加fastJson的配置信息;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        /**
         * TODO 第一个SerializerFeature.PrettyFormat可以省略，毕竟这会造成额外的内存消耗和流量，第二个是用来指定当属性值为null是是否输出：pro:null
　　　　　 * SerializerFeature.SkipTransientField
         */
        // 防止过滤掉null字段
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
        //3处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        //4.在convert中添加配置信息.
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
        return new HttpMessageConverters(converter);
    }
}