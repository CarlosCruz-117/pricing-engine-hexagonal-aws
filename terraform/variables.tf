# TODO - Parametrizaciones por entorno
#variable "environment" {
#  description = "DEV"
#  type        = string
#  default     = "dev"
#}

output "repository_url" {
  value = aws_ecr_repository.pricing_repo.repository_url
}
output "service_url" {
  value = aws_apprunner_service.pricing_service.service_url
}