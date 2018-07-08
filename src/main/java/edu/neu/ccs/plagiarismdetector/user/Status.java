package edu.neu.ccs.plagiarismdetector.user;

/**
 * Class to assemble the status tof replies after the rest requests
 */
public class Status {
    /**
     * HTTP status code
     */
    private int code;
    /**
     * HTTP status message
     */
    private String message;

    /**
     * @return HTTP status code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code HTTP status code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return HTTP status message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message HTTP status message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @param code    HTTP status code
     * @param message HTTP status message
     */
    public Status(int code, String message) {
        this.message = message;
        this.code = code;
    }

}
