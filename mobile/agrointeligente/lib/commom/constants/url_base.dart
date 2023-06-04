import 'package:flutter_dotenv/flutter_dotenv.dart';

class UrlBase {
  static String getUrlBase() {
    return dotenv.env['URL_BASE'] ?? "";
  }
}
