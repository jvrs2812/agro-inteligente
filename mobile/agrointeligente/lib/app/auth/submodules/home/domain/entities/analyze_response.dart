import 'dart:convert';
import 'dart:typed_data';

class AnalyzeResponse {
  String? url;
  DateTime? dateAnalyze;
  Uint8List? base64;

  AnalyzeResponse({this.url, this.dateAnalyze, this.base64});

  AnalyzeResponse.fromJson(Map<String, dynamic> json, {String? base64String}) {
    url = json['url'];
    dateAnalyze = DateTime.parse(
      json['date_analyze'],
    );
    if (base64String != null) base64 = base64Decode(base64String);
  }

  AnalyzeResponse copyWith(
      {String? url, String? dateAnalyze, String? base64String}) {
    DateTime date = DateTime.now();
    if (dateAnalyze != null) {
      date = DateTime.parse(dateAnalyze);
    }

    return AnalyzeResponse(
        dateAnalyze: date, url: url ?? '', base64: base64Decode(base64String!));
  }

  Map<String, dynamic> toJson() =>
      {'url': url, 'date_analyze': dateAnalyze?.toIso8601String()};
}
