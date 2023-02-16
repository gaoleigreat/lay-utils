@Configuration
@EnableTransactionManagement
@Import({DynamicDataSourceRegister.class})
public class FlywayConfig {
    private static final String BASE_SQL_LOCATION = "/db/baseMigration";
    private static final String ENCODING = "UTF-8";

    // application配置文件中base数据源配置
    @Value("${frame.druid.ds.base.url}")
    private string baseUrl;
    @Value("${frame.druid.ds.base.username}")
    private string baseUserName;
    @Value("${frame.druid.ds.base.password}")
    private string basePassword;
    @Value("${frame.druid.ds.base.driverClassName}")
    private string baseDriverClassName;

    @Bean
    public void migrate() {
        Flyway flyway = Flyway.configure()
                .dataSource(getBaseDataSource())
                .locations(BASE_SQL_LOCATION)
                .encoding(ENCODING)
                .baselineOnMigrate(true)
                .table("base_flyway")
                .load();
        flyway.migrate();
    }

    private BasicDataSource getBaseDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(baseUrl);
        dataSource.setUserName(baseUserName);
        dataSource.setPassword(basePassword);
        dataSource.setDriverClassName(baseDriverClassName);
        return dataSource;
    }
}
