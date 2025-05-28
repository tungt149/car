provider "aws" {
  region  = var.region
}
# IAM User
resource "aws_iam_user" "github_user" {
  name = "car-user"
}
resource "aws_dynamodb_table" "car" {
  name         = var.table_name
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "id"

  attribute {
    name = "id"
    type = "S"
  }

  tags = {
    Environment = var.environment
  }
}
