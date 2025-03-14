# myinvois-open-sdk

Open source effort for MyInvois API.

# Disclaimer

While this software is provided under the terms of the Apache License, Version 2.0, it comes with no warranties or guarantees. Users are free to use, modify, and distribute the software according to the terms of the license. However, by using this software, users acknowledge that they do so at their own risk and assume all responsibility for any consequences that may arise from its use.

# Current Status

Improvements compared to the original version:
- Included Lombok to reduce boilerplate within the source
- Added Builders for all the E-Invoice related classes for better DX and readability.
- Uses ZonedDateTime for better handling of Timezones and UTC.
- Uses OkHttpClient, and allow developers to pass in their own OkHttpClient instance to allow more developer controls of their API calls to external LHDN API, such as adding Interceptors for logging, etc. If OkHttpClient is not passed in, the library will create its own instance.
- Preserve an API Authorization token within an instance of "API", and only renews the token by making a call to Login API when the token is expired to improve performance. Developers are encouraged to declare the "API" instance as a Singleton.
