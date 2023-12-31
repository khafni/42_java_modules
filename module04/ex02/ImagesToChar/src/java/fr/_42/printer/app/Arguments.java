package fr._42.printer.app;


import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class Arguments {
    @Parameter(names={"--white"}, required = true)
    private String whiteC;
    @Parameter(names={"--black"}, required = true)
    private String blackC;

    public String getWhiteColor() {
        return  whiteC;
    }
    public String getBlackColor() {
        return  blackC;
    }

}
