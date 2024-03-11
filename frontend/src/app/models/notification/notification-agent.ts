import { Agent } from "../parametrage/agent";

export interface NotificationAgent {
    id?:number;
    notification?:Notification;
    agent?:Agent;
    lu?:boolean;
}

export interface GetAllNotificationAgentResponse {
    notificationAgents: NotificationAgent[];
}