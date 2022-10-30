var InputStreamCallback = Java.type("org.apache.nifi.processor.io.InputStreamCallback")
var IOUtils = Java.type("org.apache.commons.io.IOUtils")
var StandardCharsets = Java.type("java.nio.charset.StandardCharsets")

var flowFile = session.get();
if (flowFile != null) {
    // Create a new InputStreamCallback, passing in a function to define the interface method
    try {
        session.read(flowFile,
            new InputStreamCallback(function (inputStream) {
                var text = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                log.info(text)
                // Do something with text here
            }));
        flowFile = session.putAttribute(flowFile, "script-log", true)
        session.transfer(flowFile, REL_SUCCESS)
    } catch (e) {
        log.error('Something went wrong', e)
        session.transfer(flowFile, REL_FAILURE)
    }

}
