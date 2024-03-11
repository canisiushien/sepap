import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { GetAllNotificationResponse, Notification } from 'src/app/models/notification/notification';
import { GetAllNotificationAgentResponse, NotificationAgent } from 'src/app/models/notification/notification-agent';
import { environment } from 'src/environments/environment';

const resourceUrl = environment.notificationResource; 

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private http: HttpClient) { }

  getAll(event?: LazyLoadEvent): Observable<GetAllNotificationResponse> {
    return this.http.get(resourceUrl, { observe: 'response' })
    .pipe(map(response => {
        let notificationResponse: GetAllNotificationResponse = {
          notifications: response.body as Notification[]
        };
        return notificationResponse;
      }));
  }

  getAllByID(username: String,event?: LazyLoadEvent): Observable<GetAllNotificationAgentResponse> {
    return this.http.get(resourceUrl+'/agent/'+username, { observe: 'response' })
    .pipe(map(response => {
        let notificationResponse: GetAllNotificationAgentResponse = {
          notificationAgents: response.body as NotificationAgent[]
        };
        return notificationResponse;
      }));
  }

  getCount(username: String): Observable<any> {
    return this.http.get(resourceUrl+'/nonlu/'+username);
  }

  create(request: Notification): Observable<Notification> {
    return this.http.post(resourceUrl, request);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${resourceUrl}/${id}`);
  }
}
