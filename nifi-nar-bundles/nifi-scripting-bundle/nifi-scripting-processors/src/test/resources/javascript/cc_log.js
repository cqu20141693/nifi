var InputStreamCallback = Java.type("org.apache.nifi.processor.io.InputStreamCallback")
var IOUtils = Java.type("org.apache.commons.io.IOUtils")
var StandardCharsets = Java.type("java.nio.charset.StandardCharsets")

var flowFile = session.get();
if (flowFile != null) {
    try {
        var fix = fixValue.getValue()
        log.warn(text ,"fix")
        //var setLog=elLog.evaluateAttributeExpressions(flowFile).getValue()

        session.read(flowFile,
            new InputStreamCallback(function (inputStream) {
                var text = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                log.warn(text+fix)
            }));
        session.transfer(flowFile,REL_SUCCESS)
    } catch (e) {
        log.error("Something went wrong")
        session.transfer(flowFile,REL_FAILURE)
    }

}
