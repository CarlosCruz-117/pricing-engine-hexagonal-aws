// Crea un repo en ECR para almacenar la imagen Docker
resource "aws_ecr_repository" "pricing_repo" {
  name = "pricing-service"
}

// Define AWS App Runner (que ejecutará la imagen de ECR)
resource "aws_apprunner_service" "pricing_service" {
  service_name = "pricing-service"
  source_configuration {
    authentication_configuration {
      access_role_arn = aws_iam_role.apprunner_role.arn
    }
    image_repository {
      image_identifier      = "${aws_ecr_repository.pricing_repo.repository_url}:latest"
      image_repository_type = "ECR"
      image_configuration {
        port = "8080"
      }
    }
    auto_deployments_enabled = true
  }
}
