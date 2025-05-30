variable "aws_region" {
  default = "ap-southeast-1"
}

variable "cluster_name" {
  default = "car-app-cluster"
}
variable "region" {
  type = string
}

variable "table_name" {
  type = string
}

variable "environment" {
  type    = string
  default = "dev"
}
