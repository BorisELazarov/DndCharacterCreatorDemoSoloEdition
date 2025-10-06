import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Filter } from '../user-list/filter';
import { Sort } from '../../../core/sort';
import { Observable } from 'rxjs/internal/Observable';
import { UserListItem } from '../user-list/user-list-item';

@Injectable({
  providedIn: 'root'
})
export class UserManagementService {
  readonly url: string='http://localhost:8080/api/users';
  constructor(private httpClient:HttpClient) { }

  public getAll(sort:Sort, filter:Filter):Observable<HttpResponse<UserListItem[]>>{
    return this.httpClient.post<UserListItem[]>(this.url+'/getAll',
      {
        filter:filter,
        sort:sort
      },
        {observe:'response'}
    );
  }

}
