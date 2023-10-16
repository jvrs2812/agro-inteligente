class EnterpriseCreate {
  String? nameFancy;
  String? cnpj;
  String? adress;
  String? district;
  String? number;

  EnterpriseCreate(
      {this.nameFancy, this.cnpj, this.adress, this.district, this.number});

  EnterpriseCreate.fromJson(Map<String, dynamic> json) {
    nameFancy = json['name_fancy'];
    cnpj = json['cnpj'];
    adress = json['adress'];
    district = json['district'];
    number = json['number'];
  }

  EnterpriseCreate copyWith(
      {String? nameFancy,
      String? cnpj,
      String? adress,
      String? district,
      String? number}) {
    return EnterpriseCreate(
        adress: adress ?? this.adress,
        nameFancy: nameFancy ?? this.nameFancy,
        cnpj: cnpj ?? this.cnpj,
        district: district ?? this.district,
        number: number ?? this.number);
  }

  Map<String, dynamic> toJson() => {
        'name_fancy': nameFancy,
        'adress': adress,
        'cnpj': cnpj,
        'district': district,
        'number': number
      };
}
