class EnterpriseResponse {
  final String nameFancy;
  final String cnpj;
  final String adress;
  final String number;
  final String district;
  final String id;

  EnterpriseResponse(
      {this.nameFancy = "",
      this.cnpj = "",
      this.adress = "",
      this.number = "",
      this.district = "'",
      this.id = "'"});

  factory EnterpriseResponse.fromJson(Map<String, dynamic> json) {
    return EnterpriseResponse(
        nameFancy: json['name_fancy'] ?? '',
        adress: json['adress'] ?? '',
        cnpj: json['cnpj'] ?? '',
        district: json['district'] ?? '',
        id: json['id'] ?? '',
        number: json['number'] ?? '');
  }

  Map<String, dynamic> toJson() => {
        'name_fancy': nameFancy,
        'adress': adress,
        'cnpj': cnpj,
        'district': district,
        'id': id,
        'number': number
      };
}
