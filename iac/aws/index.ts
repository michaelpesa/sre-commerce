import * as pulumi from "@pulumi/pulumi";
import * as aws from "@pulumi/aws";
import * as awsx from "@pulumi/awsx";

const productRepo = new aws.ecr.Repository("product", {
    name: "product",
});

const orderRepo = new aws.ecr.Repository("order", {
    name: "order",
});

const frontendRepo = new aws.ecr.Repository("frontend", {
    name: "frontend",
});

// Export the repository URL
export const productRepoUrl = productRepo.repositoryUrl;
export const orderRepoUrl = orderRepo.repositoryUrl;
export const frontendRepoUrl = frontendRepo.repositoryUrl;

