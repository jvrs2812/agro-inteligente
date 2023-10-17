abstract class IEnterpriseFailure implements Exception {
  final String? message;
  IEnterpriseFailure({this.message});
}

abstract class EitherFailure implements Exception {
  final String? message;
  EitherFailure({this.message});
}

class NotValidParamsFailure implements IEnterpriseFailure {
  @override
  final String? message;
  NotValidParamsFailure({this.message});

  @override
  String toString() {
    return message ?? '';
  }
}

class RepositoryFailureFailure implements IEnterpriseFailure {
  @override
  final String? message;
  RepositoryFailureFailure({this.message});

  @override
  String toString() {
    return message ?? '';
  }
}

class DatasourceFailure implements IEnterpriseFailure {
  @override
  final String? message;
  DatasourceFailure({this.message});

  @override
  String toString() {
    return message ?? '';
  }
}

class SocketFailure implements IEnterpriseFailure {
  @override
  final String? message;
  SocketFailure({this.message});

  @override
  String toString() {
    return message ?? '';
  }
}
