// Crea rol para asumir posteriormente por AWS App Runner para instanciar el servicio
resource "aws_iam_role" "apprunner_role" {
  name = "apprunner-ecr-access-role"
  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Effect = "Allow"
      Principal = {
        Service = "build.apprunner.amazonaws.com"
      }
      Action = "sts:AssumeRole"
    }]
  })
}

// Asigna política de solo lectura de ECR al rol de App Runner
resource "aws_iam_role_policy_attachment" "apprunner_ecr_policy" {
  role       = aws_iam_role.apprunner_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly"
}

// Asegurar que App Runner pueda acceder correctamente al repositorio
resource "aws_ecr_repository_policy" "pricing_repo_policy" {
  repository = aws_ecr_repository.pricing_repo.name

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Sid    = "AllowAppRunnerPull"
      Effect = "Allow"
      Principal = {
        Service = "build.apprunner.amazonaws.com"  # Solo permitir App Runner; No "*" (cualquiera)
      }
      Action = [
        "ecr:GetDownloadUrlForLayer",
        "ecr:BatchGetImage",
        "ecr:BatchCheckLayerAvailability"
      ]
    }]
  })
}
