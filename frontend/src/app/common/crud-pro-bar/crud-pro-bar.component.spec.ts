import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrudProBarComponent } from './crud-pro-bar.component';

describe('CrudProBarComponent', () => {
  let component: CrudProBarComponent;
  let fixture: ComponentFixture<CrudProBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CrudProBarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CrudProBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
