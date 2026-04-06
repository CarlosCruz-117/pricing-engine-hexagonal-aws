terraform {
  # Versión mínima de Terraform para soportar las últimas funciones de AWS
  required_version = ">= 1.5.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0" # Bloquear la versión mayor para asegurar estabilidad
    }
  }
}
provider "aws" {
  region = "eu-west-1"
  # Etiquetas por defecto para que todos los recursos estén identificados
  default_tags {
    tags = {
      Project     = "EcommercePricing"
      Environment = "PoC"
      ManagedBy   = "Terraform"
      Owner       = "CarlosCruz"
    }
  }
}
