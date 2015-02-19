package nl.ipo.cds.deegree.extension.vrnfilter;

import nl.ipo.cds.properties.ConfigDirPropertyPlaceholderConfigurer;

import org.deegree.sqldialect.SQLDialect;
import org.deegree.sqldialect.postgis.PostGISDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author annes
 */

@Configuration
@ImportResource(value = {"classpath:nl/ipo/cds/dao/initDB.xml", "classpath:nl/ipo/cds/dao/dao-applicationContext.xml", "classpath:nl/ipo/cds/dao/dataSource-applicationContext.xml"})
public class FilterConfig {

    @Bean
    public SQLDialect sqlDialect() {
        return new PostGISDialect("1.5");
    }

    @Bean
    public static ConfigDirPropertyPlaceholderConfigurer properties() {
        return new ConfigDirPropertyPlaceholderConfigurer();
    }

}
