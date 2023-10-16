abstract class IAnalyzeFailure implements Exception {
  final String? message;
  IAnalyzeFailure({this.message});
}

abstract class EitherFailure implements Exception {
  final String? message;
  EitherFailure({this.message});
}

class NotValidParamsFailure implements IAnalyzeFailure {
  @override
  final String? message;
  NotValidParamsFailure({this.message});

  @override
  String toString() {
    return message ?? '';
  }
}

class RepositoryFailureFailure implements IAnalyzeFailure {
  @override
  final String? message;
  RepositoryFailureFailure({this.message});

  @override
  String toString() {
    return message ?? '';
  }
}

class DatasourceFailure implements IAnalyzeFailure {
  @override
  final String? message;
  DatasourceFailure({this.message});

  @override
  String toString() {
    return message ?? '';
  }
}
