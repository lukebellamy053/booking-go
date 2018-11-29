module.exports = function execJar(pickup, dropoff, passengers) {
    return new Promise((resolve, reject) => {
        // Get the jar location
        let jarLoc = process.env.bookingJarLoc || __dirname +  '\\..\\..\\ConsoleApp\\built\\app-1-jar-with-dependencies.jar';
        // Build the command
        let command = `java -jar ${jarLoc} -pickup ${pickup} -dropoff ${dropoff} -passengers ${passengers}`
        // Create the exec method
        let exec = require('child_process').exec, child;
        // Create the child service
        child = exec(command,
            function (error, stdout, stderr) {

                if (error) {
                    console.error(error);
                    reject(`exec error: ${error}`);
                }

                if (stderr.length > 0) {
                    console.error(stderr);
                    reject(stderr);
                } else {
                    /**
                     * Convert stdout to JSON Objects
                     * Thankfully the data is structured!
                     * {car_type} - {supplier_id} - {price}
                     */
                    stdout = stdout.replace(/\n/gi, "");
                    // Split on the line breaks
                    let lines = stdout.split("\r");
                    // Holds the end objects
                    let objects = [];
                    // Get the sections of all the lines
                    lines.forEach((line) => {
                        // Split on the dash and spaces
                        let parts = line.split(" - ");
                        // There should always be 3 segments, ignore any extra data
                        if (parts.length == 3) {
                            // Create and add the object
                            objects.push({
                                supplier_id: parts[1],
                                car_type: parts[0],
                                price: parts[2]
                            });
                        }
                    });
                    // Send the data back
                    resolve(objects);
                }
            });
    });
};