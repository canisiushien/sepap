export interface Notification {
    id?:number;
    objet?:string;
    contenu?:string;
}


export interface GetAllNotificationResponse {
    notifications: Notification[];
}