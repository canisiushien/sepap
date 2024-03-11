import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MinistereStructureComponent } from './ministere-structure.component';

describe('MinistereStructureComponent', () => {
  let component: MinistereStructureComponent;
  let fixture: ComponentFixture<MinistereStructureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MinistereStructureComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MinistereStructureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
