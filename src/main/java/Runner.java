import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: chervanev
 * Date: 17.01.13
 * Time: 17:18
 * Главный запускающий класс
 */
public class Runner {
    public static void main(String args[]) {
        Runner runner = new Runner();
        runner.start();
    }

    Acceptor mapper;

    public Runner() {
        try {
            mapper = new Acceptor(10);
            if (!loadMappings("proxy.properties")) {
                System.out.println("Unable to load properties, default settings used");
                mapper.addMapping(8885, "localhost", 5555 );
                mapper.addMapping(8888, "www.mail.ru", 80 );
            }
        } catch (IOException e) {
            // unable to start, shutdown
            e.printStackTrace();
        }

    }

    public void start() {
        mapper.run();
    }

    private boolean loadMappings(String fileName) {
        try {
            for(MappingInfo mappingInfo : PropertyLoader.loadMappings(fileName)) {
                if (mappingInfo.isComplete()) {
                    mapper.addMapping(mappingInfo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return mapper.mappingCount() != 0;
    }
}
